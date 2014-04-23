var gulp = require('gulp');
var browserify = require('gulp-browserify');
var browserify = require('gulp-browserify');
var minifyCSS = require('gulp-minify-css');
var concat = require('gulp-concat');
var watch = require('gulp-watch');
var express = require('express');

var server = express();
var serverport = process.env['PORT'] || 3000;
var buildPath = "../app/src/main/assets"
server.use(express.static(buildPath));

gulp.task('serve', function() {
  server.listen(serverport);
});

var paths = {
  statics: ['./src/**/*.html', './src/**/*.png'],
  js: ['./src/*.js'],
  watchjs: ['./src/**/*.js'],
  css: ['./src/css/*.css']
};

gulp.task('statics', function() {
  return gulp.src(paths.statics)
    .pipe(gulp.dest(buildPath));
});


gulp.task('js', function() {
  return gulp.src(paths.js)
    .pipe(browserify({}))
    .pipe(gulp.dest(buildPath));
});

gulp.task('styles', function() {
  return gulp.src(paths.css)
  .pipe(minifyCSS({
    "root": "./src/css"
  }))
  .pipe(gulp.dest(buildPath + '/css'));
})

gulp.task('watch', function() {
  gulp.watch(paths.css, ['styles']);
  gulp.watch(paths.statics, ['statics']);
  gulp.watch(paths.watchjs, ['js']);
});

gulp.task('build', [
  'statics'
   , 'js'
   , 'styles'
]);

gulp.task('default', ['build', 'watch', 'serve']);
