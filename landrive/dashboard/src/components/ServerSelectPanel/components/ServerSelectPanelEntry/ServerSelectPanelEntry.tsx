import './ServerSelectPanelEntry.css'
import React, {MouseEventHandler} from "react";
import ServerIcon from "../../../../assets/svg/server-icon.svg";

interface Props {
    name: string,
    onClick: MouseEventHandler<HTMLElement>,
}

function ServerSelectPanelEntry({name, onClick}: Props) {
    return (
        <button onClick={onClick} className="server-entry padding-medium" style={{width: '100%'}}>
            <div style={{
                height: '2rem',
                width: '2rem',
                backgroundColor: 'var(--icon-color)',
                maskRepeat: 'no-repeat',
                maskSize: 'contain',
                maskImage: `url(${ServerIcon})`
            }}>
            </div>
            {name}
        </button>
    )
}

export default ServerSelectPanelEntry