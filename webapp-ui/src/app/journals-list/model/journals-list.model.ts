export class JournalPreview {

  constructor(public readonly id: number,
              public readonly journalFullName: string) { }
}

export class JournalUserPreview {

  constructor(public readonly id: string,
              public readonly fullName: string,
              public readonly lastOnline: string,
              public readonly marker: string,
              public readonly remarksAmount: number) { }
}
