import InputBar from "../InputBar/InputBar.tsx";
import {useState} from "react";
import {createFolder} from "../../lib/api.tsx";
import './CreateFolderModal.css'

interface Props {
    server: ServerInfo,
    dir: string[],
    closeModal: () => void,
    handleRefresh: () => void
}

function CreateFolderModal({server, dir, closeModal, handleRefresh}: Props) {
    const [newName, setNewName] = useState<string>()

    return (
        <div className='inner-modal-container rename-file-modal'>
            <div>
                <span>
                    Folder name:
                </span>
                <InputBar onChange={(event) => {
                    setNewName(event.target.value)
                }}/>
            </div>
            <div>
                <button className='basic-button' onClick={() => {
                    createFolder(server, dir, newName)
                        .then(() => {
                            closeModal()
                            handleRefresh()
                        })
                }}>
                    Create Folder
                </button>
                <button className='basic-button' onClick={() => {
                    closeModal()
                }}>
                    Cancel
                </button>
            </div>
        </div>
    )
}

export default CreateFolderModal