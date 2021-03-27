export interface IClient {
  id?: number;
  name?: string;
  surname?: string;
  address?: string;
  phone?: string;
  userLogin?: string;
  userId?: number;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public address?: string,
    public phone?: string,
    public userLogin?: string,
    public userId?: number
  ) {}
}
