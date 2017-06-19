/**
 * @author liujia
 * @description
 */
define(function(require, exports, module) {
	require('javascript/step_tools');
	require('validate');
	var validate = require('javascript/dataProcessing/fromSourceToMid/validate');
	var nameSpace = "dataProcessing/"
	var validate = require('javascript/dataProcessing/fromSourceToMid/validate');
	var xmlFieldNameToPathMap, toExcludeElementList, treeNodes, //
		prevDiv, currentTreeNode, prevTreeNode, mainTableName, dBFieldList, saveXmlField = false;

	function popSetStatus(args) {
		new top.PopLayer({
			"title": "选择操作",
			"src": args.src,
			"document": top.document,
			"inData": args.inData,
			"callBack": args.callBack
		});
	} // 二次封装的弹出层工厂函数

	function generateTree(json) {
		var margin = {
			top: -40,
			right: 120,
			bottom: 0,
			left: 100
		}, width = 769 - margin.right - margin.left,
			height = 280 - margin.top - margin.bottom;

		var i = 0,
			duration = 750,
			root;

		var tree = d3.layout.tree().size([height, width]);

		var diagonal = d3.svg.diagonal().projection(function(d) {
			return [d.y, d.x];
		});

		var svg = d3.select("#basic2").append("svg").attr("width",
			width + margin.right + margin.left).attr("height",
			height + margin.top + margin.bottom).append("g").attr(
			"transform",
			"translate(" + margin.left + "," + margin.top + ")");

		root = json["trueElementTreeMap"][0];
		root.x0 = height / 2;
		root.y0 = 0;

		function update(source) {

			// 计算树形布局
			var nodes = treeNodes = tree.nodes(root).reverse(),
				links = tree
					.links(nodes);

			// 计算节点纵坐标
			nodes.forEach(function(d) {
				d.y = d.depth * 180;
			});

			// 更新节点信息
			var node = svg.selectAll("g.node").data(nodes, function(d) {
				return d.id || (d.id = ++i);
			});

			var nodeEnter = node.enter().append("g").attr("class", "node")
				.attr("transform", function(d) {
					var node = d3.select()
					return "translate(" + source.y0 + "," + source.x0 + ")";
				}).on("click", click);

			nodeEnter.append("ellipse").attr("rx", 1e-6).attr("ry", 1e-6);

			nodeEnter.append("text").attr("x", function(d) {
				return 0;
			}).attr("dy", ".35em").attr("text-anchor", function(d) {
				return "middle";
			}).text(function(d) {
				return d.name;
			}).style("fill-opacity", 1e-6);

			// Transition nodes to their new position.
			var nodeUpdate = node.transition().duration(duration).attr(
				"transform", function(d) {
					if (!d.parent) {
						var htmlFragement = "<table width='100%' border='0' cellspacing='2' cellpadding='0' class='selectTable'> " + "<tr class=''> <td class='table_td2_' width='120'><span class='table_title4'>请选择表名：</span></td>" + " <td class='table_td3_'> <select name='tableName'> </select> </td> <td class='table_td4_'> </td> </tr>" + " </table> <table width='100%' border='0' cellspacing='0' cellpadding='0' class='table_td_padding toggleDetailWrapper table_main_tr_adv'>" + " <tbody> <tr style='height:36px;'> <td align='right'> </td> <td width='60'> <input class='selectTableName btn2' type='button' value='确定' class='btn1'> " + "</td> <td width='80'> <input class='toggleDetail btn2' type='button' value='隐藏配置'> </td> <td width='80' style = 'display:none'> <input class='saveXmlField btn2' type='button' value='保存配置' class='btn2'></td></tr> </tbody> </table>";
						var $node = $("<div>").attr("id","tree_node" + d.id).data({
							"nodeType": "root",
							"name": d.name
						}).html(htmlFragement).appendTo("#basic2").hide();
					} else {
						var htmlFragement = "<table width='100%' border='0' cellspacing='2' cellpadding='0' class = 'selectTable'> " + "<tr class=''> <td class='table_td2_' width='120'><span class='table_title4'>主表名：</span> " + "<td class='table_td3_'> <input name='mainTableName' disabled class='input_Css' style='width:55%'/> </td> <td class='table_td4_'>" + " </td> </tr> <tr class=''> <td class='table_td2_' width='120'><span class='table_title4'>请选择子表名：</span></td>" + " <td class='table_td3_'> <select name='tableName'> </select> </td> <td class='table_td4_'> </td> </tr> <tr class=''>" + " <td class='table_td2_' width='120'><span class='table_title4'>外键对应关系：</span></td> <td class='table_td3_'>" + " <select name='subFKName'> </select> </td> <td class='table_td4_'> </td> </tr> </table> " + "<table width='100%' border='0' cellspacing='0' cellpadding='0' class='table_td_padding toggleDetailWrapper table_main_tr_adv'> " + "<tbody> <tr style='height:36px;'> <td align='right'> </td> <td width='60'> <input class='selectTableName btn2' type='button' value='确定' class='btn1'>" + " </td> <td width='80'> <input class='toggleDetail btn2' type='button' value='隐藏配置'> </td><td width='80' style = 'display:none'> <input class='saveXmlField btn2' type='button' value='保存配置' class='btn2'></td> </tr> </tbody> </table>";
						var $node = $("<div>").attr("id","tree_node" + d.id).data({
							"nodeType": "child",
							"name": d.name
						}).html(htmlFragement).appendTo("#basic2").hide();
					}

					var div = $("<div>")
						.attr("id", "list_container" + d.id).css(
							"display", "none");
					$("<form>").attr("id", "form" + d.id).append(div)
						.appendTo($node);
					d.div = $node;
					return "translate(" + d.y + "," + d.x + ")";
				});

			nodeUpdate.select("ellipse").attr("rx", 65).attr("ry", 35);

			nodeUpdate.select("text").style("fill-opacity", 1);

			// 进行位置变换
			var nodeExit = node.exit().transition().duration(duration).attr(
				"transform", function(d) {
					return "translate(" + source.y + "," + source.x + ")";
				}).remove();

			nodeExit.select("ellipse").attr("rx", 1e-6).attr("ry", 1e-6);

			nodeExit.select("text").style("fill-opacity", 1e-6);

			var link = svg.selectAll("path.link").data(links, function(d) {
				return d.target.id;
			});

			link.enter().insert("path", "g").attr("class", "link").attr("d",
				function(d) {
					var o = {
						x: source.x0,
						y: source.y0
					};
					return diagonal({
						source: o,
						target: o
					});
				});

			link.transition().duration(duration).attr("d", diagonal);

			link.exit().transition().duration(duration).attr("d", function(d) {
				var o = {
					x: source.x,
					y: source.y
				};
				return diagonal({
					source: o,
					target: o
				});
			}).remove();

			nodes.forEach(function(d) {
				d.x0 = d.x;
				d.y0 = d.y;
			});
		}

		update(root);
		d3.select(self.frameElement).style("height", "500px");

		function click(d) {
			$(this).parent().find("text").each(function() {
				$(this).attr("fill", "#000"); //改变当前选中的颜色
			});
			$(this).find("text").attr("fill", "#fff"); //改变当前选中的颜色
			if ($("form", prevDiv).valid()) {
				var that = currentTreeNode = this;
				for (var i = 0; i < treeNodes.length; i++) {
					treeNodes[i].div.hide();
				}

				if (saveXmlField)
					$(".saveXmlField", d.div).parent().show(); // 如果全部表单都已填好，这可以显示提交按钮

				if (!prevTreeNode) {
					prevTreeNode = this;
				}

				if (prevDiv) {
					/**/

					if (d.div.data("nodeType") == "root") {
						mainTableName = $("select[name='tableName']", prevDiv).val();
					}
					d3.select(prevTreeNode).select("ellipse").style("fill","#10B5DB");
					prevDiv.data("checked", true);
					prevTreeNode = that;
					prevDiv = null;
				}

				if (d.div.data("finish") !== "1") { // 判断是否已经填写过
					parent.loading_flag = true;
					parent.showLoading(); // 显示Loading
					$.ajax({
						url: nameSpace + "gainTableNameBySourceName.action",
						type: "post",
						data: {
							"sourceName": $("#sourceName").val()
						},
						success: function(result) {

							if (d.div.data("nodeType") == "root") {
								$("select[name='tableName']", d.div).empty();
								var tableNameOption = "<option value = ''>--请选择--</option>";
								$("select[name='tableName']", d.div).append(tableNameOption);
								for (var i = 0; i < result.tableNameList.length; i++) {
									var tableNameOption = "<option value = '" + result.tableNameList[i] + "'>" + result.tableNameList[i] + "</option>";
									$("select[name='tableName']", d.div).append(tableNameOption);
								}

							} else {

								$("input[name='mainTableName']", d.div).val(mainTableName);
								$("select[name='tableName']", d.div).empty();
								var tableNameOption = "<option value = ''>--请选择--</option>";
								$("select[name='tableName']", d.div).append(tableNameOption)
								for (var i = 0; i < result.tableNameList.length; i++) {
									var tableNameOption = "<option value = '" + result.tableNameList[i] + "'>" + result.tableNameList[i] + "</option>";
									$("select[name='tableName']", d.div).append(tableNameOption);
								}
								$("select[name='subFKName']", d.div).empty();
								var subFKNameOption = "<option value = ''>--请选择--</option>";
								$("select[name='subFKName']", d.div).append(subFKNameOption);

							}
							d.div.show();
							parent.hideLoading(); // 隐藏提示
						}
					});
				} else {
					d.div.fadeIn();
				}
			}
		}
	}
	$(".selectTableName").live("click", function(event) {
		var div = prevDiv = $(event.target).parent().parent().parent().parent()
			.parent(),
			DBFieldSelectList; // 用于每个表填写vaule用
		if ($("select[name='tableName']", div).val()) {
			if ($("select[name='subFKName']", div).val() || div.data("nodeType") == "root") {
				parent.loading_flag = true;
				parent.showLoading(); // 显示Loading
				$(".selectTable", div).toggle();
				$(".toggleDetail", div).val(function(index, value) {
					if (value.indexOf("显示") != -1)
						return "隐藏配置";
					else
						return "显示配置";
				});
				$(this).toggle();

				var flag = true; // 判断其余的节点是否都填写完成，如果是，则出现提交按钮
				for (var i = 0; i < treeNodes.length; i++) {
					if (treeNodes[i].div.attr("id") !== div.attr("id"))
						flag = flag && treeNodes[i].div.data("checked");
				}
				if (flag || saveXmlField) {
					$(".saveXmlField", div).parent().show();
					saveXmlField = true;
				}

				if (div.data("nodeType") == "root") { // 获取主表名
					mainTableName = $("select[name='tableName']", div).val();
					/*
					 * $.ajax({ url: nameSpace +
					 * "gainDBFieldByTableName.action", type: "post", data: {
					 * "tableName": mainTableName }, success: function(result) {
					 * dBFieldList = result.dBFieldList; } });
					 */
				}
				var parameters = JSON.stringify({
					"sourceName": $("#sourceName").val(),
					"typeName": $("#typeName").val(),
					"toExcludeElementList": toExcludeElementList,
					"xmlFieldToPathName": xmlFieldNameToPathMap[div.data("name")],
					"tableName": $("select[name='tableName']", div).val()
				});
				$.ajax({
					url: nameSpace + "gainDBFieldByTableName.action",
					type: "post",
					data: {
						"tableName": $("select[name='tableName']", div).val()
					},
					success: function(result) {
						var option = result.dBFieldList;
						$.ajax({
							url: nameSpace + "gainXmlFieldMap.action",
							data: {
								"parameters": parameters
							},
							type: "post",
							dataType: "json",
							success: function(json) {
								prevTreeNode = currentTreeNode;
								var result = {
									"root": []
								};
								for (var item in json) {
									for (var key in json[item]) {
										result.root.push({
											"key": key,
											"value": json[item][key]
										})
									}
								}
								$("div[id*='list_container']", div).hide();
								$("div[id*='list_container']", div).html(TrimPath.processDOMTemplate("basic2_template", result));
								var selectOption = "<option value = ''>--请选择--</option>";
								for (var item in option) {
									selectOption += "<option value = '" + option[item] + "'>" + option[item] + "</option>"
								}
								selectOption = $(selectOption);
								$("select.input_dataprocessing", div).each(
									function() {
										$(this).append(selectOption.clone()).val($(this).attr("data"));
									});
								setOddEvenLine($("div[id*='list_container']",div).attr("id"), 0); // 设置奇偶行效果
								$("div[id*='list_container']", div).fadeIn();
								$("<select>").append(selectOption).hide().addClass("select_template").appendTo(div);
								div.data("finish", "1");
								validate.valid_basic2(div);
								parent.hideLoading(); // 隐藏提示
							}
						});
					}
				});
			} else {
				alert("请选择外键关系！");
			}
		} else {
			alert("请选择表名！");
		}
		return false;
	});

	var setting = new Setting({

		id: "basic2",

		out_check: function() {
			return $("#form_createtask").valid();
		},

		on_in_opt: function() {
			/*
			 * if ($("#basic2Container").attr("isempty") == "true") {}
			 * else { validate.valid_basic2(); }
			 */
			parent.loading_flag = true;
			parent.showLoading(); // 显示Loading
			$.ajax({
				url: nameSpace + "gainXmlElementTree.action",
				type: "post",
				data: {
					"sourceName": $("#sourceName").val(),
					"typeName": $("#typeName").val()
				},
				dataType: "json",
				success: function(json) {
					xmlFieldNameToPathMap = json.xmlFieldNameToPathMap;
					toExcludeElementList = json.toExcludeElementList;
					generateTree(json);
					parent.hideLoading(); // 隐藏提示
				}
			});

		}
	});

	var init = function() {
		$(".basic2_del").live("click", function() {
			var div = $(event.target).parent().parent().parent().parent().parent().parent();
			if (confirm("确定要删除所选条目吗？")) {
				$(this).parent().parent().remove();
				setOddEvenLine($(div).attr("id"), 0);
			}
			return false;
		})

		$("#basic2_add").live("click", function() {
			var div = $(event.target).parent().parent().parent().parent().parent().parent();
			$node = $("<tr> <td><input class = 'input_dataprocessing key' value = '' name = 'xmlKey'/></td>" + " <td></td> <td><select class = 'input_dataprocessing value' value = '' name = 'xmlValue'></select></td>" + " <td></td> <td><button class = 'btn1 basic2_del' >删除</button></td> </tr>");
			$("select", $node).html($(".select_template", div.parent()).html());
			var timeStamp = new Date().getTime();
			$("input[name='xmlKey']", $node).attr("name", "xmlKey" + timeStamp);
			$("select[name='xmlValue']", $node).attr("name","xmlValue" + timeStamp);
			$node.appendTo($("tbody", $(this).parentsUntil("table").parent()));
			validate.valid_basic2(div);
			setOddEvenLine($(div).attr("id"), 0);
			return false;
		})

		$(".toggleDetail").live("click", function() {
			$(this).val(function(index, value) {
				if (value.indexOf("显示") != -1)
					return "隐藏配置";
				else
					return "显示配置";
			})
			var div = $(this).parent().parent().parent().parent().parent();
			$(".selectTable", div).toggle();
			$(".selectTableName", div).toggle();
		})
		$(".saveXmlField").live("click", function() {
			var div = $(this).parent().parent().parent().parent().parent();
			if ($("form", div).valid()) {
				/* 循环遍历获取参数 */
				var parameters = [],
					xmlFieldMap = {};
				for (var i = 0; i < treeNodes.length; i++) {
					$("tr", $("div[id*='list_container'] tbody", treeNodes[i].div)).each(function() {
						xmlFieldMap[$(".key", $(this)).val()] = $(".value",$(this)).val();
					});
					parameters.push({
						"sourceName": $("#sourceName").val(),
						"typeName": $("#typeName").val(),
						"tableName": $("select[name='tableName']",treeNodes[i].div).val(),
						"xmlFieldMap": xmlFieldMap
					})
					xmlFieldMap = {};
				}
				parameters = JSON.stringify({
					"parameters": parameters
				});

				/* 保存配置信息 */
				$.ajax({
					url: nameSpace + "saveXmlFieldMap.action",
					data: {
						"parameters": parameters
					},
					type: "post",
					dataType: "json",
					success: function(json) {
						if (json.results == "成功") {
							var parameters = [];
							var treeNodesReverse = treeNodes.reverse();
							for (var i = 0; i < treeNodesReverse.length; i++) {
								parameters.push({
									"xmlFieldToPathName": xmlFieldNameToPathMap[treeNodesReverse[i].div.data("name")],
									"tableName": $("select[name='tableName']",treeNodesReverse[i].div).val(),
									"subFKName": $("select[name='subFKName']",treeNodesReverse[i].div).val(),
									"isMainTable": treeNodesReverse[i].div.data("nodeType") == "root" ? 1 : 0
								})
							}
							parameters = {
								"parameters": parameters,
								"sourceName": $("#sourceName").val(),
								"typeName": $("#typeName").val(),
								"toExcludeElementList": toExcludeElementList
							}
							var args = {
								src: nameSpace + "toPopSelect.action",
								inData: {
									"parameters": JSON.stringify(parameters),
									"sourceName": $("#sourceName").val(),
									"typeName": $("#typeName").val()
								},
								callBack: function(mode) {
									top.PopLayer.instances[1].destroy();
									if (mode == "create")
										window.location.href = "taskConfig/toTaskListView.action";
									else
										window.location.href = "taskConfig/toTaskConfigListView.action";
								}
							};
							popSetStatus(args); // 选择操作的弹出层
						}
					}
				});
			}
		})

		$("select[name='tableName']").live("change", function() {
			parent.loading_flag = true;
			parent.showLoading(); // 显示Loading

			var div = $(this).parent().parent().parent().parent().parent();
			var tableName = $(this).val();
			$.ajax({
				url: nameSpace + "gainDBFieldByTableName.action",
				type: "post",
				data: {
					"tableName": tableName
				},
				success: function(result) {
					var option = result.dBFieldList;
					var selectOption = "<option value = ''>--请选择--</option>";
					for (var item in option) {
						selectOption += "<option value = '" + option[item] + "'>" + option[item] + "</option>"
					}
					$("select[name='subFKName']",$(div)).empty().append($(selectOption));
					parent.hideLoading(); // 隐藏提示
				}
			});
		})
	};

	module.exports = {
		setting: setting,
		init: function() {
			init();
		}
	};
});