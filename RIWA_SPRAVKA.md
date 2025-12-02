# Справка по модификации riwa и API рендеринга

## Обновления модификации
- Мод переименован в **riwa** и регистрируется через класс `com.riwa.riwa` (modid `riwa`, версия `1.0`).
- Ресурсы и код перенесены в новую структуру `flouz1/riwaclient/riwacc`.

## Точки входа
- `com.riwa.riwa` инициализирует логгер, загружает шрифты (`f1`), шейдеры рендеринга (`r1`) и иконки (`i1`), а также регистрирует обработчик наложений `k1` на шину событий Forge.

## Обработчик оверлея
- `com.riwa.k1` отслеживает клавишу **Insert** и при включении оверлея выводит в центре экрана две строки: `riwa hack` и `рива хак`.
- Рядом с текстом рисуется прямоугольник с закруглёнными углами, отрисованный через наш шейдер `rounded`.

## API шрифтов (`com.riwa.util.font.f1`)
- `a()` — загрузка всех TTF-файлов из `assets/fontrender/font` в память (`comfortaa`, `nunito`, `greycliff`, `montserrat`, `icons`).
- `a(String key, String text, float x, float y, int color, float scale)` — рендер текста указанным шрифтом и масштабом; при отсутствии шрифта использует стандартный `fontRenderer`. Возвращает ширину отрисованной строки.
- Внутренне шрифт растеризуется в `DynamicTexture` и выводится квадом через `Tessellator`.

## API иконок (`com.riwa.util.icon.i1`)
- `a()` — очистка кеша иконок.
- `a(String key, ResourceLocation icon)` — регистрация иконки по ключу.
- `a(String key, float x, float y, float w, float h)` — вывод зарегистрированной иконки по ключу.
- `b(ResourceLocation icon, float x, float y, float w, float h)` — низкоуровневый рендер `ResourceLocation` квадом с текстурными координатами.

## API рендеринга шейдеров (`com.riwa.util.render.r1`)
- `a()` — загрузка общего вершинного шейдера из `assets/rendershader/shaders/vertex.vert` и подключение фрагментных шейдеров (`rounded`, `rounded_outline`, `rounded_blur`, `rounded_gradient`).
- `a(float x, float y, float w, float h, float round, int color)` — рисование закруглённого прямоугольника с использованием шейдера `rounded`; управляет цветом, размером и радиусом скругления.
- Вспомогательные методы `d(...)`, `e(...)`, `c(...)` отвечают за сборку программы OpenGL, компиляцию шейдеров и чтение ресурсов через `ResourceLocation`.

## Пример использования
- При нажатии **Insert** активируется оверлей (см. `k1`):
  - `f1.a("comfortaa", "riwa hack", centerX - 40, centerY - 10, 0xFFFFFFFF, 1.0f);`
  - `f1.a("greycliff", "рива хак", centerX - 40, centerY + 6, 0xFFFFFFFF, 1.0f);`
  - `r1.a(boxX, boxY, 90f, 30f, 6f, 0xAA4A90E2);` рисует подсветку с закруглёнными углами.
