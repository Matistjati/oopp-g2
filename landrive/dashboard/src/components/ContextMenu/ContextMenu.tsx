import React, {
    useState,
    ReactNode,
    useEffect,
    createContext,
    useContext
} from 'react';
import './ContextMenu.css'

interface ContextMenuProviderProps {
    children: ReactNode;
}

interface ContextMenuContextType {
    showContextMenu: (event: React.MouseEvent, args: any) => void;
}

const ContextMenuContext = createContext<ContextMenuContextType | undefined>(undefined);

export const ContextMenuProvider: React.FC<ContextMenuProviderProps> = ({ children }) => {
    const [contextMenuPosition, setContextMenuPosition]
        = useState<{ top: number; left: number } | null>(null);

    const [contextMenuItems, setContextMenuItems] = useState<JSX.Element[] | null>(null);

    const closeContextMenu = () => {
        setContextMenuPosition(null);
    };

    const showContextMenu = (e: React.MouseEvent, items: any) => {
        e.preventDefault();

        const menu = items.map((item:any, index:number) => (
            <div key={index} className="contextmenu-item" onClick={() => item[1]()}>
                {item[0]}
            </div>
        ));

        setContextMenuItems([menu]);
        setContextMenuPosition({ top: e.clientY, left: e.clientX });
    };

    // If we left click outside of menu, close it
    useEffect(() => {
        const handleOutsideClick = (e: any) : void => {
            // Check if the click is outside the context menu
            if (contextMenuPosition && e.button === 0) {
                const isOutside =
                    !document.getElementById('context-menu')?.contains(e.target as Node) &&
                    !document.getElementById('context-menu-trigger')?.contains(e.target as Node);

                if (isOutside) {
                    closeContextMenu();
                }
            }
        };

        // Add event listener to the document body for left-click
        document.body.addEventListener('click', handleOutsideClick);

        return () => {
            // Cleanup: remove the event listener when the component unmounts
            document.body.removeEventListener('click', handleOutsideClick);
        };
    }, [contextMenuPosition]);

    return (
        <ContextMenuContext.Provider value={{ showContextMenu }}>
            {children}
            {contextMenuPosition && (
                <div
                    style={{
                        position: 'fixed',
                        top: contextMenuPosition.top,
                        left: contextMenuPosition.left,
                        backgroundColor: '#fff',
                        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
                        borderRadius: '20px',
                        overflow: 'hidden',
                    }}
                    onClick={closeContextMenu}
                >

                    {/* Context menu content */}
                    {contextMenuItems}
                </div>
            )}
        </ContextMenuContext.Provider>
    );
};

export const useContextMenu = () => {
    const context = useContext(ContextMenuContext);
    if (!context) {
        throw new Error('useContextMenu must be used within a ContextMenuProvider');
    }
    return context;
};

export default ContextMenuProvider;