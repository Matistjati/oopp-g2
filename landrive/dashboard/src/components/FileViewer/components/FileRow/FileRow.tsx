import './FileRow.css'
import NormalFileIcon from '../../../../assets/svg/file-icon.svg'
import {AriaAttributes, DOMAttributes} from "react";

interface Props {
    name: string,
    date: string,
    size: number,
    onClick: any,
    contextMenuItems: any[], // Too lazy to figure out type
}

function FileRow({name, date, size, onClick, contextMenuItems}: Props) {
    return (
        <tr className='file-row' onClick={onClick}>
            <td>
                <img src={NormalFileIcon} alt='' />
            </td>
            <td className='file-name' data-contextmenuitems={JSON.stringify(contextMenuItems)}>
                {name}
            </td>
            <td className='file-date' data-contextmenuitems={JSON.stringify(contextMenuItems)}>
                {date}
            </td>
            <td className='file-size' data-contextmenuitems={JSON.stringify(contextMenuItems)}>
                {size}
            </td>
        </tr>
    )
}

export default FileRow
