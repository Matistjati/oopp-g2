import { useState } from 'react';
import './FileViewer.css'
import FileRow from './FileRow/FileRow';

function FileViewer() {
    const fileRows = [];
    return (
        <table style={{width: '100%'}}>
            <tr><td></td><td>Name</td><td>Date</td><td>Size</td></tr>
            <FileRow fileName={'file_test1.file'} fileDate={'today'} fileSize={'123mb'}></FileRow>
            <FileRow fileName={'file_test234.file'} fileDate={'today'} fileSize={'12mb'}></FileRow>
            <FileRow fileName={'file_test123123.file'} fileDate={'today'} fileSize={'1mb'}></FileRow>
        </table>
    )
}

export default FileViewer
