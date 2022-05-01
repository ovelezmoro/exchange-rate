export class ExchangeResponse {
    constructor(
        public base: string,
        public amount: number,
        public result: any,
        public updated: string
        ) {}
}