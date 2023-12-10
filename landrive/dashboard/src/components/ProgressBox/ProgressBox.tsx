import './ProgressBox.css'

interface Props {
    progress: ProgressInfo
}

function ProgressBox({progress}: Props) {
    return (
        <div className={'progress-box'}>
            <div className={'text'}>
                {progress.action}: <br/> {progress.name}
            </div>
            <div className={'progress-bar-background'}>
                <div className={'progress-bar'} style={{width: `${progress.progress * 100}%`}}></div>
            </div>
        </div>
    )
}

export default ProgressBox