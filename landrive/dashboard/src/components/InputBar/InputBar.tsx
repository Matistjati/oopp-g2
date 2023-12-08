import './InputBar.css'
import {ChangeEventHandler, CSSProperties} from "react";

interface Props {
    placeholder: string,
    style?: CSSProperties | undefined
    onChange: ChangeEventHandler<HTMLInputElement> | undefined
}

function InputBar({placeholder, style, onChange}: Props) {
  return (
    <input type='text' className='input-bar padding-medium radius-medium background-3' placeholder={placeholder} style={style} onChange={onChange} />
  )
}

export default InputBar