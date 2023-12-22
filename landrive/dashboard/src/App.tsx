import './App.css'
import {ChangeEvent, ChangeEventHandler, useState} from 'react'
import useWebSocket from 'react-use-websocket';
import InputBar from './components/InputBar/InputBar'
import FileBrowser from './components/FileBrowser/FileBrowser.tsx'
import ServerSelectPanel from './components/ServerSelectPanel/ServerSelectPanel'
import {ContextMenuProvider} from './components/ContextMenu/ContextMenu.tsx'
import {emptyFilter, Filter} from './lib/interface/Filter.tsx'
import {ModalProvider} from "./components/Modal/Modal.tsx";

function App() {
    const [selectedServer, setSelectedServer] = useState<ServerInfo | null>(null);
    const [currentDirectory, setCurrentDirectory] = useState<Array<string>>([]);
    const [filter, setFilter] = useState<Filter>(emptyFilter())
    const [refreshFileServerList, setRefreshFileServerList] = useState<Boolean>(false)

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


    useWebSocket("ws://localhost:8080", {
        onMessage: (message) => {
            let messageData = JSON.parse(message.data);
            let event = messageData.event;
            let data = messageData.data;
            switch (event) {
                case "fileservers.update": 
                    setRefreshFileServerList(!refreshFileServerList);
                    break;
            }
        }
    })

    return (
        <>
            <ModalProvider>
                <ContextMenuProvider>
                    <div id='app' className='gap-medium'>
                        <ServerSelectPanel selectServer={selectServer} refreshFileServerList={refreshFileServerList}/>
                        <div id='browser-container' className='gap-medium panel' style={{flexGrow: '1'}}>
                            <div className='filter-bar' id=''>
                                <InputBar placeholder="Search" style={{width: '40rem'}} onChange={onChangeSearchBar}/>
                                <InputBar placeholder=".ext" onChange={onChangeExtBar}/>
                            </div>
                            <FileBrowser selectedServer={selectedServer} currentDirectory={currentDirectory}
                                         setCurrentDirectory={setCurrentDirectory} filter={filter}/>
                        </div>
                    </div>
                </ContextMenuProvider>
            </ModalProvider>
        </>
    )
}

export default App