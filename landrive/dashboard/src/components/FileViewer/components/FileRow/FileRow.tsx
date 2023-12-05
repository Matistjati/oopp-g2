import './FileRow.css'
import NormalFileIcon from '../../../../assets/svg/file-icon.svg'

function FileRow({name, date, size}: any) {
    return (
        <tr className='file-row'>
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
