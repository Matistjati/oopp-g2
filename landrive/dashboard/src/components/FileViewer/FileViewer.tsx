import React, {Dispatch, SetStateAction, useEffect, useState} from 'react'
import './FileViewer.css'
import RefreshButton from '../RefreshButton/RefreshButton'
import {fetchFileList, uploadFile} from '../../lib/api'
import FileRow from './components/FileRow/FileRow'
import BackButton from '../BackButton/BackButton.tsx'
import FileUploadButton from '../FileUploadButton/FileUploadButton.tsx'
import ProgressBox from '../ProgressBox/ProgressBox.tsx'

interface Props {
    selectedServer: ServerInfo | null
    currentDirectory: string[]
    setCurrentDirectory: Dispatch<SetStateAction<string[]>>
    filter: Filter
}

function FileViewer({selectedServer, currentDirectory, setCurrentDirectory}: Props) {
    const [progressing, setProgressing] = useState<ProgressInfo[]>([])
    const [fileList, setFileList] = useState<FsDirectoryList>({dirs: [], files: []})
    const [uploadCount, setUploadCount] = useState<number>(0)

    useEffect(() => {
        handleRefresh()
    }, [selectedServer, currentDirectory])

    const handleRefresh = () => {
        if (!selectedServer) return

        fetchFileList(selectedServer, currentDirectory)
            .then(serverList => {
                setFileList(serverList)
            })
            .catch(error => {
                console.error('Error fetching server list:', error)
            })
    }

    const handleBack = () => {
        if (currentDirectory.length > 0) {
            setCurrentDirectory([...currentDirectory.slice(0, -1)])
        }
        handleRefresh()
    }

    const handleFileUpload = (file: File) => {
        setUploadCount(uploadCount + 1)
        const progressInfo: ProgressInfo = {
            id: uploadCount,
            action: 'Uploading',
            name: file.name,
            progress: 0,
        }

        setProgressing(prevState => [...prevState, progressInfo])

        const updateProgress = (progress: number) => {
            setProgressing(prevState =>
                prevState.map(p => (p.id == progressInfo.id ? { ...p, progress } : p))
            )
        }

        uploadFile(file, selectedServer, currentDirectory, updateProgress)
            .then(() => {
                handleRefresh()
                setProgressing(prevState => prevState.filter(p => p.id !== progressInfo.id))
            })
            .catch(error => {
                console.error('Error uploading file:', error)
                setProgressing(prevState => prevState.filter(p => p.id !== progressInfo.id))
            })
    }

    const renderFileRows = (files: FsEntryInfo[]) =>
        files.map(file => (
            <FileRow
                key={file.name}
                name={file.name}
                date={file.date}
                size={file.size}
                onClick={() => {() => {
                    window.location.href = "http://localhost:8000/api/download/${encodeURIComponent(file.name)}";}
                }} />
        ))

    const renderDirRows = (dirs: FsEntryInfo[]) =>
        dirs.map(dir => (
            <FileRow
                key={dir.name}
                name={dir.name}
                date={dir.date}
                size={dir.size}
                onClick={() => setCurrentDirectory(prevState => prevState.concat(dir.name))}
            />
        ))

    const fileRows = renderFileRows(fileList.files)
    const dirRows = renderDirRows(fileList.dirs)

    const renderProgressBoxes = () =>
        progressing.map(progress => <ProgressBox key={progress.name} progress={progress}/>)

    return (
        <div>
            <FileUploadButton onFileUpload={handleFileUpload}/>
            <RefreshButton onClick={handleRefresh}/>
            <BackButton onClick={handleBack}/>
            <table style={{width: '100%'}}>
                <tbody>
                <tr>
                    <td></td>
                    <td>Name</td>
                    <td>Date</td>
                    <td>Size</td>
                </tr>
                {dirRows.concat(fileRows)}
                </tbody>
            </table>
            <div className={'progress-box-container gap-medium'}>{renderProgressBoxes()}</div>
        </div>
    )
}

export default FileViewer