import './InputBar.css'

function InputBar({placeholder, style, setFilter}:any) {

  return (
    <input type='text' className='input-bar padding-medium radius-medium background-3' placeholder={placeholder} style={style} onChange={(e) => setFilter({name:e.target.value})} />
  )
}

export default InputBar