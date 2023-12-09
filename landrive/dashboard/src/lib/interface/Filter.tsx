interface Filter {
    searchQuery: string,
    extQuery: string
}

function emptyFilter() : Filter {
    return {searchQuery: "", extQuery: ""}
}

function applyFilter(filter: Filter, fsEntries: FsEntryInfo[]): FsEntryInfo[] {
    return fsEntries.filter((entry) =>
        entry.name.includes(filter.searchQuery) &&
        entry.name.endsWith(filter.extQuery)
    )
}

export {applyFilter, emptyFilter}
export type {Filter}