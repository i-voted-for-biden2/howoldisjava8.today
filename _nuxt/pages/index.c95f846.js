(window.webpackJsonp=window.webpackJsonp||[]).push([[5],{326:function(e,t,n){e.exports={}},327:function(e,t,n){"use strict";n(326)},332:function(e,t,n){"use strict";n.r(t);n(37),n(12);var r=n(0);n(217);function f(e){return null!=e}var c=r.a.extend({data:function(){return{difference2:null}},computed:{difference:function(){var e=this.$moment(),t=this.$moment(13950972e5);return this.$moment.preciseDiff(e,t,!0)}},methods:{enumerate:function(e){for(var t=e.filter(f),output="",i=0;i<t.length;i++)i>0&&(output+=" ",i<=t.length-2?output+=", ":(output+=" ",output+=this.$t("and"),output+=" ")),output+=t[i];return output}}}),l=(n(327),n(75)),component=Object(l.a)(c,(function(){var e=this,t=e._self._c;e._self._setupProxy;return t("div",{staticClass:"flex flex-col justify-center items-center h-full"},[e.difference?t("p",{staticClass:"age-text"},[e._v("\n    "+e._s(e.$t("java_is_old",{specifier:e.enumerate([e.difference.years>=1?e.$tc("time.years",e.difference.years):null,e.difference.months>=1?e.$tc("time.months",e.difference.months):null,e.difference.days>=1?e.$tc("time.days",e.difference.days):null])}))+"\n  ")]):t("p",{staticClass:"age-text"},[e._v("\n    "+e._s(e.$t("java_is_old",{specifier:e.$t("loading")}))+"\n  ")])])}),[],!1,null,null,null);t.default=component.exports}}]);