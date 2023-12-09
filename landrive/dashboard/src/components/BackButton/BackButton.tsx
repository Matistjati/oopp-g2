import BackIcon from '../../assets/svg/back-icon.svg'
import './BackButton.css'
import {MouseEventHandler} from "react";

interface Props {
    onClick: MouseEventHandler<HTMLElement>
}

function BackButton({onClick}: Props) {
    return (
        <button onClick={onClick} className="back-button"
                style={{
                    height: '2rem',
                    width: '2rem',
                    backgroundColor: 'var(--icon-color)',
                    maskRepeat: 'no-repeat',
                    maskSize: 'cover',
                    maskImage: `url(${BackIcon})`
                }}>
        </button>
    )
}

export default BackButton;