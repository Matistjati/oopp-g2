import React, {Dispatch, SetStateAction, useEffect, useState} from 'react'

interface Filter{
    setName: Dispatch<SetStateAction<string>>
    setExt: Dispatch<SetStateAction<string>> 
    process: (files:FsEntryInfo[]) => FsEntryInfo[]
}

export default Filter;