export interface ICommercant {
  id?: number;
  companyName?: string;
  address?: string;
  phone?: string;
  userLogin?: string;
  userId?: number;
}

export class Commercant implements ICommercant {
  constructor(
    public id?: number,
    public companyName?: string,
    public address?: string,
    public phone?: string,
    public userLogin?: string,
    public userId?: number
  ) {}
}
