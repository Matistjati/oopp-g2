import './App.css'
import {ChangeEvent, ChangeEventHandler, useState} from 'react'
import InputBar from './components/InputBar/InputBar'
import FileViewer from './components/FileViewer/FileViewer'
import ServerSelectPanel from './components/ServerSelectPanel/ServerSelectPanel'
import {emptyFilter, Filter} from "./lib/interface/Filter.tsx";

function App() {
    const [selectedServer, setSelectedServer] = useState<ServerInfo | null>(null);
    const [currentDirectory, setCurrentDirectory] = useState<Array<string>>([]);
    const [filter, setFilter] = useState<Filter>(emptyFilter())

    const selectServer = (server: ServerInfo) => {
        setSelectedServer(server);
        setCurrentDirectory([]);
    }

    const onChangeSearchBar: ChangeEventHandler<HTMLInputElement> = (event: ChangeEvent<HTMLInputElement>) => {
        setFilter({...filter, searchQuery: event.target.value})
    }

    const onChangeExtBar: ChangeEventHandler<HTMLInputElement> = (event: ChangeEvent<HTMLInputElement>) => {
        setFilter({...filter, extQuery: event.target.value})
    }

    return (
        <>
            <div className='flex-row gap-medium padding-big background-1 text-1'
                 style={{height: '100%', width: '100%', overflow: 'auto'}}>
                <ServerSelectPanel selectServer={selectServer}/>
                <div className='flex-column gap-medium' style={{height: '100vh', flexGrow: '1'}}>
                    <div className='flex-row panel radius-medium gap-medium' id='filter-panel'>
                        <InputBar placeholder="Search" style={{width: '40rem'}} onChange={onChangeSearchBar}/>
                        <InputBar placeholder=".ext" onChange={onChangeExtBar}/>
                    </div>
                    <div className='panel radius-medium background-2 padding-medium' id='file-browser-panel'>
                        <FileViewer
                            selectedServer={selectedServer}
                            currentDirectory={currentDirectory}
                            setCurrentDirectory={setCurrentDirectory}
                            filter={filter}
                        />
                    </div>
                </div>
            </div>
        </>
    )
}

export default App
