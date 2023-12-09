import { useState, useEffect } from 'react';
import RefreshButton from '../RefreshButton/RefreshButton';
import { fetchFileServerList } from '../../lib/api';
import ServerSelectPanelEntry from './components/ServerSelectPanelEntry/ServerSelectPanelEntry.tsx';
import './ServerSelectPanel.css'

interface Props {
    selectServer: (serverInfo: ServerInfo) => void
}

function ServerSelectPanel({selectServer}: Props) {
    const [serverList, setServerList] = useState<Array<ServerInfo>>([]);

    useEffect(() => {
        handleRefresh()
    }, []);

    const handleRefresh = () => {
        fetchFileServerList()
            .then(serverList => {
                setServerList(serverList);
            })
            .catch(error => {
                console.error('Error fetching file list:', error);
            });
    };

    const serverEntries = serverList.map(serverInfo => (
        <ServerSelectPanelEntry onClick={() => {
            selectServer(serverInfo);
        }} name={serverInfo.name} />
    ));

    return (
        <div className='panel' id='server-select-panel'>
            <div className='server-select-panel-top'>
                <span>Available storage units</span>
                <RefreshButton onClick={handleRefresh} />
            </div>
            {serverEntries}
        </div>
    );
}

export default ServerSelectPanel;