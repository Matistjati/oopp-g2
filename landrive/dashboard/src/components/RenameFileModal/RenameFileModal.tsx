import InputBar from "../InputBar/InputBar.tsx";
import {useState} from "react";
import {renameFile} from "../../lib/api.tsx";
import './RenameFileModal.css'

interface Props {
    file: FsEntryInfo,
    server: ServerInfo,
    dir: string[],
    closeModal: () => void,
    handleRefresh: () => void
}

function RenameFileModal({file, server, dir, closeModal, handleRefresh}: Props) {
    const [newName, setNewName] = useState(file.name)

    return (
        <div className='inner-modal-container rename-file-modal'>
            <div>
                <span>
                    New name:
                </span>
                <InputBar onChange={(event) => {
                    setNewName(event.target.value)
                }}/>
            </div>
            <div>
                <button className='basic-button' onClick={() => {
                    renameFile(file, server, dir, newName)
                        .then(() => {
                            closeModal()
                            handleRefresh()
                        })
                }}>
                    Rename
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

export default RenameFileModal