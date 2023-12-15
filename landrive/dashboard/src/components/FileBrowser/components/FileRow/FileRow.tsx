import './FileRow.css'
import NormalFileIcon from '../../../../assets/svg/fileicons/file-icon.svg'
import FolderIcon from '../../../../assets/svg/fileicons/folder-icon.svg'
import PngIcon from '../../../../assets/svg/fileicons/file-type-png-icon.svg'
import JpegIcon from '../../../../assets/svg/fileicons/file-type-jpg-icon.svg'
import SvgIcon from '../../../../assets/svg/fileicons/file-type-svg-icon.svg'
import PdfIcon from '../../../../assets/svg/fileicons/file-type-pdf-icon.svg'
import MusicIcon from '../../../../assets/svg/fileicons/file-music-icon.svg'
import TextIcon from '../../../../assets/svg/fileicons/file-text-icon.svg'
import SettingsIcon from '../../../../assets/svg/fileicons/file-settings-icon.svg'
import DocIcon from '../../../../assets/svg/fileicons/file-type-doc-icon.svg'
import DocxIcon from '../../../../assets/svg/fileicons/file-type-docx-icon.svg'
import ZipIcon from '../../../../assets/svg/fileicons/file-type-zip-icon.svg'
import React, {MouseEventHandler} from "react";
import { useContextMenu } from '../../../ContextMenu/ContextMenu.tsx';

interface Props {
    name: string,
    date: string,
    size: number,
    type: string,
    downloadHandler: () => void,
    renameHandler: () => void,
    deleteHandler: () => void,
    onClick: MouseEventHandler<HTMLElement>
}

function FileRow({name, date, size, type, downloadHandler, renameHandler, deleteHandler, onClick}: Props) {
    const { showContextMenu } = useContextMenu();

    function enableContextMenu(event: React.MouseEvent, name: string) {
        const menu = []
        if (type == "file") {
            menu.push(["Download", downloadHandler])
        }
        menu.push(["Rename", renameHandler])
        menu.push(["Delete", deleteHandler])
        showContextMenu(event, menu);
    }

    let icon;
    switch (type) {
        case "folder":
            icon = FolderIcon
            break
        case "file":
            switch (name.split('.').pop()) {
                case 'png':
                    icon = PngIcon
                    break
                case 'jpeg':
                    icon = JpegIcon
                    break
                case 'jpg':
                    icon = JpegIcon
                    break
                case 'svg':
                    icon = SvgIcon
                    break
                case 'wav':
                    icon = MusicIcon
                    break
                case 'mp3':
                    icon = MusicIcon
                    break
                case 'txt':
                    icon = TextIcon
                    break
                case 'pdf':
                    icon = PdfIcon
                    break
                case 'csv':
                    icon = SettingsIcon
                    break
                case 'ini':
                    icon = SettingsIcon
                    break
                case 'json':
                    icon = SettingsIcon
                    break
                case 'doc':
                    icon = DocIcon
                    break
                case 'docx':
                    icon = DocxIcon
                    break
                case 'zip':
                    icon = ZipIcon
                    break
                case 'rar':
                    icon = ZipIcon
                    break
                case '7zip':
                    icon = ZipIcon
                    break
                default:
                    icon = NormalFileIcon
            }
            break
        default:
            icon = NormalFileIcon
    }

    let sizeText
    const logSize = Math.log(size) / Math.log(1024)
    if (logSize < 1) {
        sizeText = `${size} B`
    }
    else if (logSize < 2) {
        sizeText = `${Math.round(size / 1024 * 100) / 100} KB`
    }
    else if (logSize < 3) {
        sizeText = `${Math.round(size / (1024 * 1024) * 100) / 100} MB`
    }
    else {
        sizeText = `${Math.round(size / (1024 * 1024 * 1024) * 100) / 100} GB`
    }

    return (
        <tr className='file-row' onClick={onClick} onContextMenu=
            {(event) => {enableContextMenu(event, name)}}>
            <td>
                <div style={{
                    height: '2rem',
                    width: '2rem',
                    backgroundColor: 'var(--icon-color)',
                    maskRepeat: 'no-repeat',
                    maskSize: 'contain',
                    maskImage: `url(${icon})`
                }}>
                </div>
            </td>
            <td className='file-name'>
                {name}
            </td>
            <td className='file-date'>
                {date}
            </td>
            <td className='file-size'>
                {sizeText}
            </td>
        </tr>
    )
}

export default FileRow
