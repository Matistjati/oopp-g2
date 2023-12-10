import React, {Dispatch, SetStateAction, useContext, useEffect, useState} from 'react'
import './FileBrowser.css'
import RefreshButton from '../RefreshButton/RefreshButton'
import {downloadFile, fetchFileList, uploadFile} from '../../lib/api'
import FileRow from './components/FileRow/FileRow'
import BackButton from '../BackButton/BackButton.tsx'
import FileUploadButton from '../FileUploadButton/FileUploadButton.tsx'
import ProgressBox from '../ProgressBox/ProgressBox.tsx'
import {Filter, applyFilter} from "../../lib/interface/Filter.tsx";
import DirectoryBar from "./components/DirectoryBar/DirectoryBar.tsx";
import {ModalConsumer, ModalContext} from "../Modal/Modal.tsx";
import RenameFileModal from "../RenameFileModal/RenameFileModal.tsx";

interface Props {
    selectedServer: ServerInfo | null
    currentDirectory: string[]
    setCurrentDirectory: Dispatch<SetStateAction<string[]>>
    filter: Filter
}

function FileBrowser({selectedServer, currentDirectory, setCurrentDirectory, filter}: Props) {
    const [progressing, setProgressing] = useState<ProgressInfo[]>([])
    const [fsDirectoryList, setFsDirectoryList] = useState<FsDirectoryList>({files: [], dirs: []})
    const [filteredFileList, setFilteredFileList] = useState<FsEntryInfo[]>([])
    const [filteredDirList, setFilteredDirList] = useState<FsEntryInfo[]>([])
    const [uploadCount, setUploadCount] = useState<number>(0)

    const modalState = useContext(ModalContext);

    const handleRefresh = () => {
        if (!selectedServer) return
        fetchFileList(selectedServer, currentDirectory)
            .then(fsDirectoryList => {
                setFsDirectoryList(fsDirectoryList)
            })
            .catch(error => {
                console.error('Error fetching server list:', error)
            })
    }

    useEffect(() => {
        handleRefresh()
    }, [selectedServer, currentDirectory]);

    useEffect(() => {
        setFilteredFileList(applyFilter(filter, fsDirectoryList.files))
        setFilteredDirList(applyFilter(filter, fsDirectoryList.dirs))
    }, [filter, fsDirectoryList]);

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

    const renameHandler = (file: FsEntryInfo) => {
        modalState.openModal(<RenameFileModal
            file={file}
            server={selectedServer}
            dir={currentDirectory}
            closeModal={modalState.closeModal}
            handleRefresh={handleRefresh} />)
    }

    const renderFileRows = (files: FsEntryInfo[]) =>
        files.map(file => (
            <FileRow
                name={file.name}
                date={file.date}
                size={file.size}
                type={"file"}
                downloadHandler={() => {
                    downloadFile(file, currentDirectory)
                }}
                deleteHandler={() => {

                }}
                renameHandler={() => {renameHandler(file)}}
                onClick={() => {

                }}
            />
        ))

    const renderDirRows = (dirs: FsEntryInfo[]) =>
        dirs.map(dir => (
            <FileRow
                key={dir.name}
                name={dir.name}
                date={dir.date}
                size={dir.size}
                type={"folder"}
                downloadHandler={() => {

                }}
                deleteHandler={() => {

                }}
                renameHandler={() => {

                }}
                onClick={() => setCurrentDirectory(prevState => prevState.concat(dir.name))}
            />
        ))

    const fileRows = renderFileRows(filteredFileList)
    const dirRows = renderDirRows(filteredDirList)

    const renderProgressBoxes = () =>
        progressing.map(progress => <ProgressBox key={progress.name} progress={progress}/>)

    return (
        <div id='file-browser'>
            <div id='file-browser-buttons'>
                <BackButton onClick={handleBack}/>
                <RefreshButton onClick={handleRefresh}/>
                <FileUploadButton onFileUpload={handleFileUpload}/>
            </div>
            <DirectoryBar selectedServer={selectedServer} currentDirectory={currentDirectory} setCurrentDirectory={setCurrentDirectory}/>
            <div id='file-row-container'>
                <table style={{width: '100%'}}>
                    <tbody>
                    <tr>
                        <td style={{width: '4.5rem'}}></td>
                        <td style={{width: '50rem'}}>Name</td>
                        <td style={{width: '20rem'}}>Date</td>
                        <td>Size</td>
                    </tr>
                    {dirRows.concat(fileRows)}
                    {/* <FileRow name={"test.png"} date={"2023-11-05"} size={1000} type={"file"} onClick={() => {}} /> */}
                    </tbody>
                </table>
            </div>
            <div className={'progress-box-container gap-medium'}>{renderProgressBoxes()}</div>
        </div>
    )
}

export default FileBrowser