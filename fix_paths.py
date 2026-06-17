import os, json

d = 'src/main/resources/assets/craftablecapes/models/item'
c = 0
for f in os.listdir(d):
    if not f.endswith('.json'): continue
    with open(os.path.join(d, f), 'r') as fp:
        data = json.load(fp)
    old = data['textures']['layer0']
    new = old.replace('capes/', 'item/')
    data['textures']['layer0'] = new
    with open(os.path.join(d, f), 'w') as fp:
        json.dump(data, fp, indent=2)
    c += 1
print(f'Updated {c} models: capes/ -> item/')
