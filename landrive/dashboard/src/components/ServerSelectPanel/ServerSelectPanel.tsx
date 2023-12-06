import { useState, useEffect } from 'react';
import RefreshButton from '../RefreshButton/RefreshButton';
import { fetchFileServerList } from '../../lib/api';
import ServerSelectPanelEntry from './components/ServerSelectPanelEntry/ServerSelectPanelEntry.tsx';

function ServerSelectPanel({selectServer}: any) {
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
        <div style={{ alignItems: 'center' }} className='panel flex-column radius-medium background-2 padding-big' id='server-select-panel'>
            <div className='flex-row gap-small' style={{ justifyContent: 'center', alignItems: 'center' }}>
                <span>Available storage units</span>
                <RefreshButton onClick={handleRefresh} />
            </div>
            {serverEntries}
        </div>
    );
}

export default ServerSelectPanel;