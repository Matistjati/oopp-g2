import './ServerEntry.css'

function ServerEntry({name}: any) {
    return(
        <div className="server-entry padding-medium" style={{width: '100%'}}>{name}</div>
    )
}

export default ServerEntry