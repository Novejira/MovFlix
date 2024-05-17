
# MovFlix

"Mov" biasanya merupakan singkatan dari "movie" atau "film" dalam bahasa Inggris, sedangkan "Flix" adalah istilah yang sering digunakan sebagai singkatan dari "flicks", yang juga berarti film atau film-film. Jadi, "MovFlix" menggabungkan kedua istilah tersebut menjadi sebuah nama yang menggambarkan aplikasi sebagai platform untuk menilai dan meliha list film yang ada.


## API Reference

### Base URL
Dalam aplikasi ini, kita mengunakan sebuah free API yang disediakan oleh The Movie Database (TMDB) dengan base URL yang telah kita dapatkan setelah melakukan register. Berikut merupakan bas API yang kami gunakan :
`https://api.themoviedb.org/3/`


#### Get Now Playing Movie
```http
@GET movie/now_playing
```

#### Get Popular Movie
```http
@GET movie/popular
```

#### Get Top Rated Movie
```http
@GET movie/top_rated
```

#### Get Upcoming Movie
```http
@GET movie/upcoming
```

| Query | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `language` | `string` | **Required**. Your Language For Response|
| `page` | `int` | **Required**. Your Response Data Page |

## Team Member

- [Muhammad Asnafi Alkaromi](https://github.com/asnafialkaromi) (Ketua)
- [Rafi Ammar Dinata](https://github.com/rafiammr) (Wakil Ketua)
- [Raihan Rafi Rizqullah](https://github.com/RaihanRafi44) (Anggota)
- [Novejira Angela Pello](https://github.com/Novejira) (Anggota)
## Preview App

![App Screenshot](https://cdn.discordapp.com/attachments/1239546292674498601/1240885565155709060/Preview_1.png?ex=6648303d&is=6646debd&hm=afd945337082edacaebc2eed5b7af8f2c5642af61559ab6bb23fa9fc205bb575&)

![App Screenshot](https://cdn.discordapp.com/attachments/1239546292674498601/1240886055490551848/Preview_2.png?ex=664830b2&is=6646df32&hm=2df8c8d903aa29312a9bf1306e15ce9c934d41091a56d2bbcd8d442a482f286a&)

