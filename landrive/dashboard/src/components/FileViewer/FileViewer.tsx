import {useEffect, useState} from 'react';
import './FileViewer.css'
import RefreshButton from '../RefreshButton/RefreshButton';
import {fetchFileList} from '../../lib/api';
import FileRow from './components/FileRow/FileRow';
import BackButton from "../BackButton/BackButton.tsx";

interface Props {
    selectedServer: ServerInfo | null,
    currentDirectory: Array<string>,
    setCurrentDirectory: any
}

function FileViewer({selectedServer, currentDirectory, setCurrentDirectory}: Props) {
    const [fileList, setFileList] = useState<FsDirectoryList>({dirs: [], files: []});

    useEffect(() => {
        handleRefresh()
    }, [selectedServer, currentDirectory]);

    const handleRefresh = () => {
        if (selectedServer == null) {
            return;
        }
        fetchFileList(selectedServer, currentDirectory)
            .then(serverList => {
                setFileList(serverList);
            })
            .catch(error => {
                console.error('Error fetching server list:', error);
            });
    };

    const handleBack = () => {
        if (currentDirectory.length > 0) {
            setCurrentDirectory(currentDirectory.splice(0, -1));
        }
        handleRefresh();
    }

    const dirRows =
        fileList.dirs.map((dir) => (
            <FileRow name={dir.name} date={dir.date} size={dir.size} onClick={() => {
                setCurrentDirectory(currentDirectory.concat([dir.name]))
            }} />
        ))

    const fileRows =
        fileList.files.map((file) => (
            <FileRow name={file.name} date={file.date} size={file.size} onClick={() => {}} />
        ))

    return (
        <div>
            <RefreshButton onClick={handleRefresh} />
            <BackButton onClick={handleBack} />
            <table style={{ width: '100%' }}>
                <tbody>
                <tr><td></td><td>Name</td><td>Date</td><td>Size</td></tr>
                {dirRows.concat(fileRows)}
                </tbody>
            </table>
        </div>
    )
}

export default FileViewer
