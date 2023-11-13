import './FileRow.css'
import NormalFileIcon from '../../assets/svg/file-icon.svg'

function FileRow({fileName, fileDate, fileSize}: any) {
    return (
        <tr className='file-row'>
            <td>
                <img src={NormalFileIcon} alt='' />
            </td>
            <td className='file-name'>
                {fileName}
            </td>
            <td className='file-date'>
                {fileDate}
            </td>
            <td className='file-size'>
                {fileSize}
            </td>
        </tr>
    )
}

export default FileRow
