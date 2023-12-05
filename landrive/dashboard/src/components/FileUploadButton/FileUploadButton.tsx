// FileUploadButton.tsx
import React, { ChangeEvent } from 'react';

interface FileUploadButtonProps {
    onFileUpload: (file: File) => void;
}

const FileUploadButton: React.FC<FileUploadButtonProps> = ({ onFileUpload }) => {
    const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            onFileUpload(file);
        }
    };

    return (
        <div>
            <input type="file" onChange={handleFileChange} />
        </div>
    );
};



export default FileUploadButton;
