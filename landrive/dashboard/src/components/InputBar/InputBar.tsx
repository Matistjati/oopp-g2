import './InputBar.css'

function InputBar({placeholder, style}: any) {
    return (
        <input type='text' className='input-bar padding-medium radius-medium background-3' placeholder={placeholder}
               style={style}/>
    )
}

export default InputBar