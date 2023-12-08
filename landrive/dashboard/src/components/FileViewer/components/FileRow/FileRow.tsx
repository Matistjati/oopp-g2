import './FileRow.css'
import NormalFileIcon from '../../../../assets/svg/file-icon.svg'
import React, {AriaAttributes, DOMAttributes, useState} from "react";
import { useContextMenu } from '../../../ContextMenu/ContextMenu.tsx';

interface Props {
    name: string,
    date: string,
    size: number,
    onClick: any,
}

function FileRow({name, date, size, onClick}: Props) {
    const { showContextMenu } = useContextMenu();
    function enableContextMenu(event: any, name: string) {
        let menu = []
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
