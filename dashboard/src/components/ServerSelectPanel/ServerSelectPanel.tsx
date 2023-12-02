import { useState, useEffect } from 'react';
import RefreshButton from '../RefreshButton/RefreshButton';
import { fetchFileServerList } from '../../lib/api';
import ServerEntry from './components/ServerEntry';
import Delimiter from '../Delimiter/Delimiter';

function ServerSelectPanel({selectServer}: any) {
    const [serverList, setServerList] = useState<Array<String>>([]);

    useEffect(() => {
        handleRefresh()
    }, []);

    const handleRefresh = () => {
        fetchFileServerList()
            .then(serverList => {
                setServerList(serverList);
            })
            .catch(error => {
                console.error('Error fetching server list:', error);
            });
    };

    const serverEntries = serverList.map(name => (
        <ServerEntry onClick={() => {
            selectServer(name);
        }} name={name} />
    ));

    return (
        <div style={{ alignItems: 'center' }} className='panel flex-column radius-medium background-2 padding-big' id='server-select-panel'>
            <div className='flex-row gap-small' style={{ justifyContent: 'center', alignItems: 'center' }}>
                <span>Available storage units</span>
                <RefreshButton onClick={handleRefresh} />
            </div>
            <Delimiter />
            {serverEntries}
        </div>
    );
}

export default ServerSelectPanel;