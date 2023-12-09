import './DirectoryBar.css'
import RightArrow from '../../../../assets/svg/right-arrow.svg'
import {Dispatch, SetStateAction} from "react";

interface Props {
    selectedServer: ServerInfo | null,
    currentDirectory: string[],
    setCurrentDirectory: Dispatch<SetStateAction<string[]>>
}

function DirectoryBar({selectedServer, currentDirectory, setCurrentDirectory}: Props) {
    let inner: JSX.Element
    const dirs: JSX.Element[] = []
    if (selectedServer != null) {
        for (const [idx, dir] of currentDirectory.entries()) {
            dirs.push(
                <>
                    <div style={{
                        height: '2rem',
                        width: '2rem',
                        backgroundColor: 'var(--icon-color)',
                        maskRepeat: 'no-repeat',
                        maskSize: 'contain',
                        maskImage: `url(${RightArrow})`
                    }}></div>
                    <button onClick={() => {
                        setCurrentDirectory(currentDirectory.slice(idx))
                    }}>
                        {dir}
                    </button>
                </>
            )
        }
        inner = (
            <div className='directory-bar'>
                <button onClick={() => {
                    setCurrentDirectory([])
                }}>
                    {selectedServer.name}
                </button>
                {dirs}
            </div>
        )
    }
    else {
        inner = (
            <div className='directory-bar'></div>
        )
    }

    return (
        inner
    )
}

export default DirectoryBar