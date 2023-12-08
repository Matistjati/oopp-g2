class Filter {
    name: string
    ext: string

    constructor(name: string, ext: string) {
        this.name = name
        this.ext = ext
    }

    setName(name: string) {
        this.name = name
    }

    setExt(ext: string) {
        this.ext = ext
    }

    apply(fsEntries: FsEntryInfo[]) : FsEntryInfo[] {
        return fsEntries.filter((entry) =>
            entry.name.includes(this.name) &&
            entry.name.endsWith(this.ext)
        )
    }
}

export {Filter}