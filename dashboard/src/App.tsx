import { useState } from 'react'
import './App.css'
import InputBar from './components/InputBar/InputBar'
import FileViewer from './components/FileViewer/FileViewer'

function App() {
  return (
    <>
      <div className='flex-row gap-medium padding-big background-1 text-1' style={{height: '100%', width: '100%', overflow: 'auto'}}>
        <div style={{alignItems: 'center'}} className='panel flex-column radius-medium background-2 padding-big' id='server-select-panel'>
          <span>Available storage units</span>
        </div>
        <div className='flex-column gap-medium' style={{height: '100%', flexGrow: '1'}}>
          <div className='flex-row panel radius-medium gap-medium' id='filter-panel'>
            <InputBar placeholder="Search" style={{width: '40rem'}}/>
            <InputBar placeholder=".ext" />
          </div>
          <div className='panel radius-medium background-2 padding-medium' id='file-browser-panel'>
            <FileViewer />
          </div>
        </div>
      </div>
    </>
  )
}

export default App
