import React, {createContext} from "react";
import './Modal.css'

const ModalContext = createContext<State>({innerNode: null, openModal: () => {}, closeModal: () => {} })

interface Props {
    children: React.ReactNode
}

interface State {
    innerNode: React.ReactNode | null
    openModal: (node: React.ReactNode) => void
    closeModal: () => void
}

class ModalProvider extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props)
        this.state = {
            innerNode: null,
            openModal: this.openModal,
            closeModal: this.closeModal
        }
    }

    renderModal(): React.ReactNode {
        return (this.state.innerNode == null ? <></> :
            <div className='modal-container'>
                {this.state.innerNode}
            </div>
        )
    }

    closeModal = () => {
        this.setState({
            innerNode: null
        })
    }

    openModal = (node: React.ReactNode) => {
        this.setState({
            innerNode: node
        })
    }

    render() {
        return (
            <ModalContext.Provider value={this.state}>
                {this.renderModal()}
                {this.props.children}
            </ModalContext.Provider>
        )
    }
}

const ModalConsumer = ModalContext.Consumer

export { ModalProvider, ModalContext, ModalConsumer }