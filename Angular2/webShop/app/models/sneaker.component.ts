/**
 * Created by Hrvoje on 28.3.2016..
 */

export class Sneaker{
    id: number;
    name: string;
    description: string;
    photo: string;
    price: number;

    constructor(id: number, name: string, description: string, photo: string, price: number){
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.price = price;
    }
}
