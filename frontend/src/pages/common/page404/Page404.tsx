import style from "./Page404.module.css";

export const Page404 = () => {
  return (
    <>
      <h1 className={style.no_found}>404</h1>
      <h2 className={style.no_found2}>페이지를 찾을수가 없어요</h2>
      <div className={style.the_container}>
        <input type="checkbox" id="toggle" />
        <label htmlFor="toggle"></label>

        <div className={style.day_night_cont}>
          <span className={style.the_sun}></span>
          <div className={style.the_moon}>
            <span className={style.moon_inside}></span>
          </div>
        </div>

        <div className={style.switch}>
          <div className={style.button}>
            <div className={style.b_inside}></div>
          </div>
        </div>

        <div className={style.c_window}>
          <span className={style.the_sun}></span>
          <span className={style.the_moon}></span>

          <div className={style.the_cat}>
            <div className={style.cat_face}>
              <section className={style.eyes + " " + style.left}>
                <span className={style.pupil}></span>
              </section>
              <section className={style.eyes + " " + style.right}>
                <span className={style.pupil}></span>
              </section>

              <span className={style.nose}></span>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
