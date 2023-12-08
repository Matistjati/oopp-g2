import React, {useState, ReactNode, MouseEvent, useEffect} from 'react';
import './ContextMenu.css'

interface ContextMenuModuleProps {
    children: ReactNode;
}

const ContextMenu: React.FC<ContextMenuModuleProps> = ({ children }) => {
    const [contextMenuPosition, setContextMenuPosition]
        = useState<{ top: number; left: number } | null>(null);
    const [contextMenuItems, setContextMenuItems] = useState<JSX.Element[] | null>(null);

    const handleContextMenu = (e: any) => {
        e.preventDefault();

        console.log(e.target);
        console.log(e.target.keys);
        const v = JSON.parse(e.target.dataset.contextmenuitems);
        console.log(v);
        console.log(v[0])
        console.log(v[1])
        if (v!=null)
        {
            const items = v.map((item:any, index:number) => (
                <div key={index} className="contextmenu-item" onClick={() => eval(item[1])}>
                    {item[0]}
                </div>
            ));


            setContextMenuItems([items]);
            console.log(v);
            setContextMenuPosition({ top: e.clientY, left: e.clientX });

        }

    };

    const closeContextMenu = () => {
        setContextMenuPosition(null);
    };

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
        <>
            <div onContextMenu={handleContextMenu}>{children}</div>

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
        </>
    );
};

export default ContextMenu;
