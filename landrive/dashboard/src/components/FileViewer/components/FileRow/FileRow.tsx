import './FileRow.css'
import NormalFileIcon from '../../../../assets/svg/file-icon.svg'
import React, {MouseEventHandler} from "react";
import { useContextMenu } from '../../../ContextMenu/ContextMenu.tsx';

interface Props {
    name: string,
    date: string,
    size: number,
    onClick: MouseEventHandler<HTMLElement>,
}

function FileRow({name, date, size, onClick}: Props) {
    const { showContextMenu } = useContextMenu();

    function enableContextMenu(event: React.MouseEvent, name: string) {
        const menu = []
        menu.push(["Download", ()=>{console.log("Downloaded"+name)}])
        showContextMenu(event, menu);
    }

    return (
        <tr className='file-row' onClick={onClick} onContextMenu=
            {(event) => {enableContextMenu(event, name)}}>
            <td>
                <img src={NormalFileIcon} alt='' />
            </td>
            <td className='file-name'>
                {name}
            </td>
            <td className='file-date'>
                {date}
            </td>
            <td className='file-size'>
                {size}
            </td>
        </tr>
    )
}

export default FileRow
