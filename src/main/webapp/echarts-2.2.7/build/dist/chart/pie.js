define("echarts/chart/pie", ["require", "./base", "zrender/shape/Text", "zrender/shape/Ring", "zrender/shape/Circle", "zrender/shape/Sector", "zrender/shape/Polyline", "../config", "../util/ecData", "zrender/tool/util", "zrender/tool/math", "zrender/tool/color", "../chart"], function (e) {
	function t(e, t, n, a, o) {
		i.call(this, e, t, n, a, o);
		var r = this;
		r.shapeHandler.onmouseover = function (e) {
			var t = e.target,
			i = h.get(t, "seriesIndex"),
			n = h.get(t, "dataIndex"),
			a = h.get(t, "special"),
			o = [t.style.x, t.style.y],
			s = t.style.startAngle,
			l = t.style.endAngle,
			d = ((l + s) / 2 + 360) % 360,
			c = t.highlightStyle.color,
			m = r.getLabel(i, n, a, o, d, c, !0);
			m && r.zr.addHoverShape(m);
			var p = r.getLabelLine(i, n, o, t.style.r0, t.style.r, d, c, !0);
			p && r.zr.addHoverShape(p)
		},
		this.refresh(a)
	}
	var i = e("./base"),
	n = e("zrender/shape/Text"),
	a = e("zrender/shape/Ring"),
	o = e("zrender/shape/Circle"),
	r = e("zrender/shape/Sector"),
	s = e("zrender/shape/Polyline"),
	l = e("../config");
	l.pie = {
		zlevel : 0,
		z : 2,
		clickable : !0,
		legendHoverLink : !0,
		center : ["50%", "50%"],
		radius : [0, "75%"],
		clockWise : !0,
		startAngle : 90,
		minAngle : 0,
		selectedOffset : 10,
		itemStyle : {
			normal : {
				borderColor : "rgba(0,0,0,0)",
				borderWidth : 1,
				label : {
					show : !0,
					position : "outer"
				},
				labelLine : {
					show : !0,
					length : 20,
					lineStyle : {
						width : 1,
						type : "solid"
					}
				}
			},
			emphasis : {
				borderColor : "rgba(0,0,0,0)",
				borderWidth : 1,
				label : {
					show : !1
				},
				labelLine : {
					show : !1,
					length : 20,
					lineStyle : {
						width : 1,
						type : "solid"
					}
				}
			}
		}
	};
	var h = e("../util/ecData"),
	d = e("zrender/tool/util"),
	c = e("zrender/tool/math"),
	m = e("zrender/tool/color");
	return t.prototype = {
		type : l.CHART_TYPE_PIE,
		_buildShape : function () {
			var e = this.series,
			t = this.component.legend;
			this.selectedMap = {},
			this._selected = {};
			var i,
			n,
			r;
			this._selectedMode = !1;
			for (var s, d = 0, c = e.length; c > d; d++)
				if (e[d].type === l.CHART_TYPE_PIE) {
					if (e[d] = this.reformOption(e[d]), this.legendHoverLink = e[d].legendHoverLink || this.legendHoverLink, s = e[d].name || "", this.selectedMap[s] = t ? t.isSelected(s) : !0, !this.selectedMap[s])
						continue;
					i = this.parseCenter(this.zr, e[d].center),
					n = this.parseRadius(this.zr, e[d].radius),
					this._selectedMode = this._selectedMode || e[d].selectedMode,
					this._selected[d] = [],
					this.deepQuery([e[d], this.option], "calculable") && (r = {
							zlevel : e[d].zlevel,
							z : e[d].z,
							hoverable : !1,
							style : {
								x : i[0],
								y : i[1],
								r0 : n[0] <= 10 ? 0 : n[0] - 10,
								r : n[1] + 10,
								brushType : "stroke",
								lineWidth : 1,
								strokeColor : e[d].calculableHolderColor || this.ecTheme.calculableHolderColor || l.calculableHolderColor
							}
						}, h.pack(r, e[d], d, void 0, -1), this.setCalculable(r), r = n[0] <= 10 ? new o(r) : new a(r), this.shapeList.push(r)),
					this._buildSinglePie(d),
					this.buildMark(d)
				}
			this.addShapeList()
		},
		_buildSinglePie : function (e) {
			for (var t, i = this.series, n = i[e], a = n.data, o = this.component.legend, r = 0, s = 0, l = 0, h = Number.NEGATIVE_INFINITY, d = [], c = 0, m = a.length; m > c; c++)
				t = a[c].name, this.selectedMap[t] = o ? o.isSelected(t) : !0, this.selectedMap[t] && !isNaN(a[c].value) && (0 !== +a[c].value ? r++ : s++, l += +a[c].value, h = Math.max(h, +a[c].value));
			if (0 !== l) {
				for (var p, u, g, V, U, y, f = 100, _ = n.clockWise, b = (n.startAngle.toFixed(2) - 0 + 360) % 360, x = n.minAngle || .01, k = 360 - x * r - .01 * s, v = n.roseType, c = 0, m = a.length; m > c; c++)
					if (t = a[c].name, this.selectedMap[t] && !isNaN(a[c].value)) {
						if (u = o ? o.getColor(t) : this.zr.getColor(c), f = a[c].value / l, p = "area" != v ? _ ? b - f * k - (0 !== f ? x : .01) : f * k + b + (0 !== f ? x : .01) : _ ? b - 360 / m : 360 / m + b, p = p.toFixed(2) - 0, f = (100 * f).toFixed(2), g = this.parseCenter(this.zr, n.center), V = this.parseRadius(this.zr, n.radius), U = +V[0], y = +V[1], "radius" === v ? y = a[c].value / h * (y - U) * .8 + .2 * (y - U) + U : "area" === v && (y = Math.sqrt(a[c].value / h) * (y - U) + U), _) {
							var L;
							L = b,
							b = p,
							p = L
						}
						this._buildItem(d, e, c, f, a[c].selected, g, U, y, b, p, u),
						_ || (b = p)
					}
				this._autoLabelLayout(d, g, y);
				for (var c = 0, m = d.length; m > c; c++)
					this.shapeList.push(d[c]);
				d = null
			}
		},
		_buildItem : function (e, t, i, n, a, o, r, s, l, d, c) {
			var m = this.series,
			p = ((d + l) / 2 + 360) % 360,
			u = this.getSector(t, i, n, a, o, r, s, l, d, c);
			h.pack(u, m[t], t, m[t].data[i], i, m[t].data[i].name, n),
			e.push(u);
			var g = this.getLabel(t, i, n, o, p, c, !1),
			V = this.getLabelLine(t, i, o, r, s, p, c, !1);
			V && (h.pack(V, m[t], t, m[t].data[i], i, m[t].data[i].name, n), e.push(V)),
			g && (h.pack(g, m[t], t, m[t].data[i], i, m[t].data[i].name, n), g._labelLine = V, e.push(g))
		},
		getSector : function (e, t, i, n, a, o, s, l, h, d) {
			var p = this.series,
			u = p[e],
			g = u.data[t],
			V = [g, u],
			U = this.deepMerge(V, "itemStyle.normal") || {},
			y = this.deepMerge(V, "itemStyle.emphasis") || {},
			f = this.getItemStyleColor(U.color, e, t, g) || d,
			_ = this.getItemStyleColor(y.color, e, t, g) || ("string" == typeof f ? m.lift(f,  - .2) : f),
			b = {
				zlevel : u.zlevel,
				z : u.z,
				clickable : this.deepQuery(V, "clickable"),
				style : {
					x : a[0],
					y : a[1],
					r0 : o,
					r : s,
					startAngle : l,
					endAngle : h,
					brushType : "both",
					color : f,
					lineWidth : U.borderWidth,
					strokeColor : U.borderColor,
					lineJoin : "round"
				},
				highlightStyle : {
					color : _,
					lineWidth : y.borderWidth,
					strokeColor : y.borderColor,
					lineJoin : "round"
				},
				_seriesIndex : e,
				_dataIndex : t
			};
			if (n) {
				var x = ((b.style.startAngle + b.style.endAngle) / 2).toFixed(2) - 0;
				b.style._hasSelected = !0,
				b.style._x = b.style.x,
				b.style._y = b.style.y;
				var k = this.query(u, "selectedOffset");
				b.style.x += c.cos(x, !0) * k,
				b.style.y -= c.sin(x, !0) * k,
				this._selected[e][t] = !0
			} else
				this._selected[e][t] = !1;
			return this._selectedMode && (b.onclick = this.shapeHandler.onclick),
			this.deepQuery([g, u, this.option], "calculable") && (this.setCalculable(b), b.draggable = !0),
			(this._needLabel(u, g, !0) || this._needLabelLine(u, g, !0)) && (b.onmouseover = this.shapeHandler.onmouseover),
			b = new r(b)
		},
		getLabel : function (e, t, i, a, o, r, s) {
			var l = this.series,
			h = l[e],
			m = h.data[t];
			if (this._needLabel(h, m, s)) {
				var p,
				u,
				g,
				V = s ? "emphasis" : "normal",
				U = d.merge(d.clone(m.itemStyle) || {}, h.itemStyle),
				y = U[V].label,
				f = y.textStyle || {},
				_ = a[0],
				b = a[1],
				x = this.parseRadius(this.zr, h.radius),
				k = "middle";
				y.position = y.position || U.normal.label.position,
				"center" === y.position ? (p = _, u = b, g = "center") : "inner" === y.position || "inside" === y.position ? (x = (x[0] + x[1]) * (y.distance || .5), p = Math.round(_ + x * c.cos(o, !0)), u = Math.round(b - x * c.sin(o, !0)), r = "#fff", g = "center") : (x = x[1] - -U[V].labelLine.length, p = Math.round(_ + x * c.cos(o, !0)), u = Math.round(b - x * c.sin(o, !0)), g = o >= 90 && 270 >= o ? "right" : "left"),
				"center" != y.position && "inner" != y.position && "inside" != y.position && (p += "left" === g ? 20 : -20),
				m.__labelX = p - ("left" === g ? 5 : -5),
				m.__labelY = u;
				var v = new n({
						zlevel : h.zlevel,
						z : h.z + 1,
						hoverable : !1,
						style : {
							x : p,
							y : u,
							color : f.color || r,
							text : this.getLabelText(e, t, i, V),
							textAlign : f.align || g,
							textBaseline : f.baseline || k,
							textFont : this.getFont(f)
						},
						highlightStyle : {
							brushType : "fill"
						}
					});
				return v._radius = x,
				v._labelPosition = y.position || "outer",
				v._rect = v.getRect(v.style),
				v._seriesIndex = e,
				v._dataIndex = t,
				v
			}
		},
		getLabelText : function (e, t, i, n) {
			var a = this.series,
			o = a[e],
			r = o.data[t],
			s = this.deepQuery([r, o], "itemStyle." + n + ".label.formatter");
			return s ? "function" == typeof s ? s.call(this.myChart, {
				seriesIndex : e,
				seriesName : o.name || "",
				series : o,
				dataIndex : t,
				data : r,
				name : r.name,
				value : r.value,
				percent : i
			}) : "string" == typeof s ? (s = s.replace("{a}", "{a0}").replace("{b}", "{b0}").replace("{c}", "{c0}").replace("{d}", "{d0}"), s = s.replace("{a0}", o.name).replace("{b0}", r.name).replace("{c0}", r.value).replace("{d0}", i)) : void 0 : r.name
		},
		getLabelLine : function (e, t, i, n, a, o, r, l) {
			var h = this.series,
			m = h[e],
			p = m.data[t];
			if (this._needLabelLine(m, p, l)) {
				var u = l ? "emphasis" : "normal",
				g = d.merge(d.clone(p.itemStyle) || {}, m.itemStyle),
				V = g[u].labelLine,
				U = V.lineStyle || {},
				y = i[0],
				f = i[1],
				_ = a,
				b = this.parseRadius(this.zr, m.radius)[1] - -V.length,
				x = c.cos(o, !0),
				k = c.sin(o, !0);
				return new s({
					zlevel : m.zlevel,
					z : m.z + 1,
					hoverable : !1,
					style : {
						pointList : [[y + _ * x, f - _ * k], [y + b * x, f - b * k], [p.__labelX, p.__labelY]],
						strokeColor : U.color || r,
						lineType : U.type,
						lineWidth : U.width
					},
					_seriesIndex : e,
					_dataIndex : t
				})
			}
		},
		_needLabel : function (e, t, i) {
			return this.deepQuery([t, e], "itemStyle." + (i ? "emphasis" : "normal") + ".label.show")
		},
		_needLabelLine : function (e, t, i) {
			return this.deepQuery([t, e], "itemStyle." + (i ? "emphasis" : "normal") + ".labelLine.show")
		},
		_autoLabelLayout : function (e, t, i) {
			for (var n = [], a = [], o = 0, r = e.length; r > o; o++)
				("outer" === e[o]._labelPosition || "outside" === e[o]._labelPosition) && (e[o]._rect._y = e[o]._rect.y, e[o]._rect.x < t[0] ? n.push(e[o]) : a.push(e[o]));
			this._layoutCalculate(n, t, i, -1),
			this._layoutCalculate(a, t, i, 1)
		},
		_layoutCalculate : function (e, t, i, n) {
			function a(t, i, n) {
				for (var a = t; i > a; a++)
					if (e[a]._rect.y += n, e[a].style.y += n, e[a]._labelLine && (e[a]._labelLine.style.pointList[1][1] += n, e[a]._labelLine.style.pointList[2][1] += n), a > t && i > a + 1 && e[a + 1]._rect.y > e[a]._rect.y + e[a]._rect.height)
						return void o(a, n / 2);
				o(i - 1, n / 2)
			}
			function o(t, i) {
				for (var n = t; n >= 0 && (e[n]._rect.y -= i, e[n].style.y -= i, e[n]._labelLine && (e[n]._labelLine.style.pointList[1][1] -= i, e[n]._labelLine.style.pointList[2][1] -= i), !(n > 0 && e[n]._rect.y > e[n - 1]._rect.y + e[n - 1]._rect.height)); n--);
			}
			function r(e, t, i, n, a) {
				for (var o, r, s, l = i[0], h = i[1], d = a > 0 ? t ? Number.MAX_VALUE : 0 : t ? Number.MAX_VALUE : 0, c = 0, m = e.length; m > c; c++)
					r = Math.abs(e[c]._rect.y - h), s = e[c]._radius - n, o = n + s > r ? Math.sqrt((n + s + 20) * (n + s + 20) - Math.pow(e[c]._rect.y - h, 2)) : Math.abs(e[c]._rect.x + (a > 0 ? 0 : e[c]._rect.width) - l), t && o >= d && (o = d - 10), !t && d >= o && (o = d + 10), e[c]._rect.x = e[c].style.x = l + o * a, e[c]._labelLine && (e[c]._labelLine.style.pointList[2][0] = l + (o - 5) * a, e[c]._labelLine.style.pointList[1][0] = l + (o - 20) * a), d = o
			}
			e.sort(function (e, t) {
				return e._rect.y - t._rect.y
			});
			for (var s, l = 0, h = e.length, d = [], c = [], m = 0; h > m; m++)
				s = e[m]._rect.y - l, 0 > s && a(m, h, -s, n), l = e[m]._rect.y + e[m]._rect.height;
			this.zr.getHeight() - l < 0 && o(h - 1, l - this.zr.getHeight());
			for (var m = 0; h > m; m++)
				e[m]._rect.y >= t[1] ? c.push(e[m]) : d.push(e[m]);
			r(c, !0, t, i, n),
			r(d, !1, t, i, n)
		},
		reformOption : function (e) {
			var t = d.merge;
			return e = t(t(e || {}, d.clone(this.ecTheme.pie || {})), d.clone(l.pie)),
			e.itemStyle.normal.label.textStyle = this.getTextStyle(e.itemStyle.normal.label.textStyle),
			e.itemStyle.emphasis.label.textStyle = this.getTextStyle(e.itemStyle.emphasis.label.textStyle),
			this.z = e.z,
			this.zlevel = e.zlevel,
			e
		},
		refresh : function (e) {
			e && (this.option = e, this.series = e.series),
			this.backupShapeList(),
			this._buildShape()
		},
		addDataAnimation : function (e, t) {
			function i() {
				s--,
				0 === s && t && t()
			}
			for (var n = this.series, a = {}, o = 0, r = e.length; r > o; o++)
				a[e[o][0]] = e[o];
			var s = 0,
			h = {},
			d = {},
			c = {},
			m = this.shapeList;
			this.shapeList = [];
			for (var p, u, g, V = {}, o = 0, r = e.length; r > o; o++)
				p = e[o][0], u = e[o][2], g = e[o][3], n[p] && n[p].type === l.CHART_TYPE_PIE && (u ? (g || (h[p + "_" + n[p].data.length] = "delete"), V[p] = 1) : g ? V[p] = 0 : (h[p + "_-1"] = "delete", V[p] = -1), this._buildSinglePie(p));
			for (var U, y, o = 0, r = this.shapeList.length; r > o; o++)
				switch (p = this.shapeList[o]._seriesIndex, U = this.shapeList[o]._dataIndex, y = p + "_" + U, this.shapeList[o].type) {
				case "sector":
					h[y] = this.shapeList[o];
					break;
				case "text":
					d[y] = this.shapeList[o];
					break;
				case "polyline":
					c[y] = this.shapeList[o]
				}
			this.shapeList = [];
			for (var f, o = 0, r = m.length; r > o; o++)
				if (p = m[o]._seriesIndex, a[p]) {
					if (U = m[o]._dataIndex + V[p], y = p + "_" + U, f = h[y], !f)
						continue;
					if ("sector" === m[o].type)
						"delete" != f ? (s++, this.zr.animate(m[o].id, "style").when(400, {
								startAngle : f.style.startAngle,
								endAngle : f.style.endAngle
							}).done(i).start()) : (s++, this.zr.animate(m[o].id, "style").when(400, V[p] < 0 ? {
								startAngle : m[o].style.startAngle
							}
								 : {
								endAngle : m[o].style.endAngle
							}).done(i).start());
					else if ("text" === m[o].type || "polyline" === m[o].type)
						if ("delete" === f)
							this.zr.delShape(m[o].id);
						else
							switch (m[o].type) {
							case "text":
								s++,
								f = d[y],
								this.zr.animate(m[o].id, "style").when(400, {
									x : f.style.x,
									y : f.style.y
								}).done(i).start();
								break;
							case "polyline":
								s++,
								f = c[y],
								this.zr.animate(m[o].id, "style").when(400, {
									pointList : f.style.pointList
								}).done(i).start()
							}
				}
			this.shapeList = m,
			s || t && t()
		},
		onclick : function (e) {
			var t = this.series;
			if (this.isClick && e.target) {
				this.isClick = !1;
				for (var i, n = e.target, a = n.style, o = h.get(n, "seriesIndex"), r = h.get(n, "dataIndex"), s = 0, d = this.shapeList.length; d > s; s++)
					if (this.shapeList[s].id === n.id) {
						if (o = h.get(n, "seriesIndex"), r = h.get(n, "dataIndex"), a._hasSelected)
							n.style.x = n.style._x, n.style.y = n.style._y, n.style._hasSelected = !1, this._selected[o][r] = !1;
						else {
							var m = ((a.startAngle + a.endAngle) / 2).toFixed(2) - 0;
							n.style._hasSelected = !0,
							this._selected[o][r] = !0,
							n.style._x = n.style.x,
							n.style._y = n.style.y,
							i = this.query(t[o], "selectedOffset"),
							n.style.x += c.cos(m, !0) * i,
							n.style.y -= c.sin(m, !0) * i
						}
						this.zr.modShape(n.id)
					} else
						this.shapeList[s].style._hasSelected && "single" === this._selectedMode && (o = h.get(this.shapeList[s], "seriesIndex"), r = h.get(this.shapeList[s], "dataIndex"), this.shapeList[s].style.x = this.shapeList[s].style._x, this.shapeList[s].style.y = this.shapeList[s].style._y, this.shapeList[s].style._hasSelected = !1, this._selected[o][r] = !1, this.zr.modShape(this.shapeList[s].id));
				this.messageCenter.dispatch(l.EVENT.PIE_SELECTED, e.event, {
					selected : this._selected,
					target : h.get(n, "name")
				}, this.myChart),
				this.zr.refreshNextFrame()
			}
		}
	},
	d.inherits(t, i),
	e("../chart").define("pie", t),
	t
});
