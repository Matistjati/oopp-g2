import './ProgressBox.css'

interface Props {
    progress: ProgressInfo
}

function ProgressBox({progress}: Props) {
    return (
        <div className={'progress-box background-3 flex-column'} style={{justifyContent: 'flex-start'}}>
            <div className={'text'}>
                {progress.action}: <br/> {progress.name}
            </div>
            <div className={'progress-bar-background background-2'}>
                <div className={'progress-bar background-4'} style={{width: `${progress.progress * 100}%`}}></div>
            </div>
        </div>
    )
}

export default ProgressBox