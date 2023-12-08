import React, {Dispatch, SetStateAction, useEffect, useState} from 'react'
import Filter from './interface/Filter.tsx'

function fileFilter():Filter {

    const [name, setName] = useState<string>("");
    const [ext, setExt] = useState<string>("");

function process(files:FsEntryInfo[]): FsEntryInfo[]{
    const namesMatches:FsEntryInfo[] = files.filter(file => file.name.includes(name));
    return namesMatches.filter(file => file.name.includes(ext));
}



const FilterObj:Filter = {
    setName:setName,
    setExt:setExt,
    process:process
}


    return(FilterObj)
}

export default fileFilter