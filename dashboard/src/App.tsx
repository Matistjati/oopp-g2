import { useState } from 'react'
import './App.css'
import InputBar from './components/InputBar/InputBar'
import FileViewer from './components/FileViewer/FileViewer'
import { fetchFileServerList } from './lib/api'
import ServerSelectPanel from './components/ServerSelectPanel/ServerSelectPanel'
import FileUploadButton from './components/FileUploadButton/FileUploadButton'
import handleFileUpload from "./components/FileUploadButton/HandleFileUpload.tsx";

function App() {
  const [selectedServer, setSelectedServer] = useState<string>(''); 
  const [currentDirectory, setCurrentDirectory] = useState<Array<string>>([]);
  
  const selectServer = (name: string) => {
      setSelectedServer(name);
      setCurrentDirectory([]);
      console.log(name);
  }

  return (
    <>
      <div className='flex-row gap-medium padding-big background-1 text-1' style={{height: '100%', width: '100%', overflow: 'auto'}}>
        <ServerSelectPanel selectServer={selectServer} />
        <div className='flex-column gap-medium' style={{height: '100%', flexGrow: '1'}}>
          <div className='flex-row panel radius-medium gap-medium' id='filter-panel'>
            <InputBar placeholder="Search" style={{width: '40rem'}}/>
            <InputBar placeholder=".ext" />
            <FileUploadButton onFileUpload={handleFileUpload} /> {/* Include the file upload button */}
          </div>
          <div className='panel radius-medium background-2 padding-medium' id='file-browser-panel'>
            <FileViewer selectedServer={selectedServer} currentDirectory={currentDirectory} />
          </div>
        </div>
      </div>
    </>
  )
}

export default App
