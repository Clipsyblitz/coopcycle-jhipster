export interface ICoursier {
  id?: number;
  name?: string;
  surname?: string;
  transportMean?: string;
  phone?: string;
  userLogin?: string;
  userId?: number;
}

export class Coursier implements ICoursier {
  constructor(
    public id?: number,
    public name?: string,
    public surname?: string,
    public transportMean?: string,
    public phone?: string,
    public userLogin?: string,
    public userId?: number
  ) {}
}
