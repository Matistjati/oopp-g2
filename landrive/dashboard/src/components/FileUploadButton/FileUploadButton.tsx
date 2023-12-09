// FileUploadButton.tsx
import React, {ChangeEvent} from 'react';
import './FileUploadButton.css'

interface Props {
    onFileUpload: (file: File) => void;
}

const FileUploadButton = ({onFileUpload}: Props) => {
    const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            onFileUpload(file);
        }
    };

    return (
        <label className='basic-button file-upload-button-label'>
            Upload File
            <input className='file-upload-button' type="file" onChange={handleFileChange}/>
        </label>
    );
};

export default FileUploadButton;
