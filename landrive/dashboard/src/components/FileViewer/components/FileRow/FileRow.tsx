import './FileRow.css'
import NormalFileIcon from '../../../../assets/svg/file-icon.svg'

interface Props {
    name: string,
    date: string,
    size: number,
    onClick: any
}

function FileRow({name, date, size, onClick}: Props) {
    return (
        <tr className='file-row' onClick={onClick}>
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
