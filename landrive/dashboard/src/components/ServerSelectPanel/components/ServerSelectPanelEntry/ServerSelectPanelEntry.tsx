import './ServerSelectPanelEntry.css'

function ServerSelectPanelEntry({name, onClick}: any) {
    return(
        <button onClick={onClick} className="server-entry padding-medium" style={{width: '100%'}}>{name}</button>
    )
}

export default ServerSelectPanelEntry