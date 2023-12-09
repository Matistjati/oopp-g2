import RefreshIcon from '../../assets/svg/refresh-icon.svg'
import './RefreshButton.css'
import {MouseEventHandler} from "react";

interface Props {
    onClick: MouseEventHandler<HTMLElement>
}

function RefreshButton({onClick}: Props) {
    return (
        <button onClick={onClick} className="refresh-button"
                style={{
                    height: '2rem',
                    width: '2rem',
                    backgroundColor: 'var(--icon-color)',
                    maskRepeat: 'no-repeat',
                    maskSize: 'cover',
                    maskImage: `url(${RefreshIcon})`
                }}>
        </button>
    )
}

export default RefreshButton;