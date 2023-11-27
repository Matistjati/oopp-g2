import RefreshIcon from '../../assets/svg/refresh-icon.svg'
import './RefreshButton.css'

function RefreshButton({onClick}:any) {
  return (
    <button onClick={onClick} className="refresh-button" style={{height: '2rem'}}>
        <img src={RefreshIcon} alt="" style={{height: '100%'}}/>
    </button>
  )
}

export default RefreshButton;