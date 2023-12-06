// FileUploadButton.tsx
import React, { ChangeEvent } from 'react';
import {uploadFile} from "../../lib/api.tsx";

interface Props {
    onFileUpload: (file: File) => void;
}

const FileUploadButton = ({ onFileUpload }: Props) => {
    const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (file) {
            onFileUpload(file);
        }
    };

    return (
        <div>
            {/* @ts-expect-error */}
            <input type="file" webkitdirectory="" directory="" onChange={handleFileChange} />
        </div>
    );
};

export default FileUploadButton;
