import BackIcon from '../../assets/svg/back-icon.svg'
import './BackButton.css'

function BackButton({onClick}:any) {
  return (
    <button onClick={onClick} className="back-button" style={{height: '2rem'}}>
        <img src={BackIcon} alt="" style={{height: '100%'}}/>
    </button>
  )
}

export default BackButton;